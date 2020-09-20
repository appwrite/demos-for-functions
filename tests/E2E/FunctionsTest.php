<?php

namespace Tests\E2E\Services\Teams;

use Tests\E2E\Client;
use PHPUnit\Framework\TestCase;

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

        return [
            'session' => $session,
            'projectId' => $projectId,
        ];
       
    }

    /**
     * @depends testCreateProject
     */
    public function testListProject($data):array
    {
        var_dump($data);
        return [];
    }
}