<?php

namespace Tests\E2E;

use CURLFile;
use Tests\E2E\Client;
use PHPUnit\Framework\TestCase;
use Utopia\CLI\Console;

class FunctionsTest extends TestCase
{
    /**
     * @var Client
     */
    protected $client = null;

    /**
     * @var string
     */
    protected $endpoint = 'http://localhost/v1';

    protected function setUp(): void
    {
        $this->client = new Client();

        $this->client
            ->setEndpoint($this->endpoint)
        ;
    }

    public function testCreateProject(): array
    {
        /**
         * Test for SUCCESS
         */
        $email = rand().'email@tests.appwrite.io';
        $password = 'password';

        $root = $this->client->call(Client::METHOD_POST, '/account', array_merge([
            'origin' => 'http://localhost',
            'content-type' => 'application/json',
            'x-appwrite-project' => 'console',
        ], [
        ]), [
            'email' => $email,
            'password' => $password,
            'name' => 'User Name',
        ]);

        var_dump($root);

        $this->assertEquals(201, $root['headers']['status-code']);

        $session = $this->client->call(Client::METHOD_POST, '/account/sessions', [
            'origin' => 'http://localhost',
            'content-type' => 'application/json',
            'x-appwrite-project' => 'console',
        ], [
            'email' => $email,
            'password' => $password,
        ]);

        $session = $this->client->parseCookie((string)$session['headers']['set-cookie'])['a_session_console'];

        $team = $this->client->call(Client::METHOD_POST, '/teams', array_merge([
            'origin' => 'http://localhost',
            'content-type' => 'application/json',
            'x-appwrite-project' => 'console',
            'cookie' => 'a_session_console=' . $session,
        ], [
        ]), [
            'name' => 'Project Test',
        ]);

        $this->assertEquals(201, $team['headers']['status-code']);
        $this->assertEquals('Project Test', $team['body']['name']);
        $this->assertNotEmpty($team['body']['$id']);

        $response = $this->client->call(Client::METHOD_POST, '/projects', [
            'origin' => 'http://localhost',
            'content-type' => 'application/json',
            'x-appwrite-project' => 'console',
            'cookie' => 'a_session_console=' . $session,
        ], [
            'name' => 'Project Test',
            'teamId' => $team['body']['$id'],
        ]);

        $this->assertEquals(201, $response['headers']['status-code']);

        $projectId = $response['body']['$id'];

        $key = $this->client->call(Client::METHOD_POST, '/projects/' . $projectId . '/keys', [
            'origin' => 'http://localhost',
            'content-type' => 'application/json',
            'cookie' => 'a_session_console=' . $session,
            'x-appwrite-project' => 'console',
        ], [
            'name' => 'Demo Project Key',
            'scopes' => [
                'users.read',
                'users.write',
                'teams.read',
                'teams.write',
                'collections.read',
                'collections.write',
                'documents.read',
                'documents.write',
                'files.read',
                'files.write',
                'functions.read',
                'functions.write',
                'locale.read',
                'avatars.read',
                'health.read',
            ],
        ]);

        $apiKey = $key['body']['secret'] ?? '';

        $this->assertEquals(201, $key['headers']['status-code']);
        $this->assertNotEmpty($key['body']);
        $this->assertNotEmpty($key['body']['secret']);

        return [
            'session' => $session,
            'projectId' => $projectId,
            'apiKey' => $apiKey,
        ];
    }

    /**
     * @depends testCreateProject
     */
    public function testFunctions($data):array
    {
        $projectId = $data['projectId'] ?? '';
        $apiKey = $data['apiKey'] ?? '';

        $envs = json_decode(file_get_contents(__DIR__.'/../../functions.json'), true);

        foreach ($envs as $key => $env) {

            $language = $env['language'] ?? '';
            $version = $env['version'] ?? '';
            $name = $env['env'] ?? '';
            $output = $env['output'] ?? '';
            $target = __DIR__.'/function';
            $code = $target.'/code.tar.gz';
            $entrypoint = $env['entrypoint'] ?? '';
            $timeout = $env['timeout'] ?? 15;
            $path = $env['path'] ?? '';
            $packaging = $env['packaging'] ?? [];
            $build = array_merge([
                'rm -r '.$target,
                'cp -r '.realpath(__DIR__.'/../../'.$path).' '.$target,
            ],
            $packaging,
            [
                'docker run --rm -v '.$target.':/app -w /app appwrite/env-php-8.0:1.0.0 tar -zcvf code.tar.gz .'
            ]);

            foreach ($build as $key => $command) {
                $stdin = '';
                $stdout = '';
                $stderr = '';
                Console::execute($command, $stdin, $stdout, $stderr);
            }

            $function = $this->client->call(Client::METHOD_POST, '/functions', array_merge([
                'content-type' => 'application/json',
                'x-appwrite-project' => $projectId,
                'x-appwrite-key' => $apiKey,
            ]), [
                'name' => 'Test '.$name,
                'env' => $name,
                'vars' => [
                ],
                'events' => [],
                'schedule' => '',
                'timeout' => $timeout,
            ]);

            $functionId = $function['body']['$id'] ?? '';
    
            $this->assertEquals(201, $function['headers']['status-code']);

            $tag = $this->client->call(Client::METHOD_POST, '/functions/'.$functionId.'/tags', array_merge([
                'content-type' => 'multipart/form-data',
                'x-appwrite-project' => $projectId,
                'x-appwrite-key' => $apiKey,
            ]), [
                'command' => $entrypoint,
                'code' => new CURLFile($code, 'application/x-gzip', basename($code)),
            ]);

            $tagId = $tag['body']['$id'] ?? '';
            $this->assertEquals(201, $tag['headers']['status-code']);

            $tag = $this->client->call(Client::METHOD_PATCH, '/functions/'.$functionId.'/tag', array_merge([
                'content-type' => 'application/json',
                'x-appwrite-project' => $projectId,
                'x-appwrite-key' => $apiKey,
            ]), [
                'tag' => $tagId,
            ]);
    
            $this->assertEquals(200, $tag['headers']['status-code']);
           
            $execution = $this->client->call(Client::METHOD_POST, '/functions/'.$functionId.'/executions', array_merge([
                'content-type' => 'application/json',
                'x-appwrite-project' => $projectId,
                'x-appwrite-key' => $apiKey,
            ]), [
                'async' => 1,
            ]);

            $executionId = $execution['body']['$id'] ?? '';
            $this->assertEquals(201, $execution['headers']['status-code']);

            sleep(15);

            $executions = $this->client->call(Client::METHOD_GET, '/functions/'.$functionId.'/executions', array_merge([
                'content-type' => 'application/json',
                'x-appwrite-project' => $projectId,
                'x-appwrite-key' => $apiKey,
            ]));

            if($executions['body']['executions'][0]['status'] === 'failed') {
                var_dump($executions['body']['executions'][0]);
            }
    
            $this->assertEquals($executions['headers']['status-code'], 200);
            $this->assertEquals($executions['body']['sum'], 1);
            $this->assertIsArray($executions['body']['executions']);
            $this->assertCount(1, $executions['body']['executions']);
            $this->assertEquals($executions['body']['executions'][0]['$id'], $executionId);
            $this->assertEquals($executions['body']['executions'][0]['trigger'], 'http');
            $this->assertEquals($executions['body']['executions'][0]['status'], 'completed');
            $this->assertEquals($executions['body']['executions'][0]['exitCode'], 0);
            $this->assertEquals($executions['body']['executions'][0]['stdout'], $output);
        }

        return [
            'functionId' => $functionId,
        ];
    }
}