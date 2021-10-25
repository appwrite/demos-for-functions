const {
    url,
    method,
    headers: headersInit,
    body,
} = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA") ?? "{}");

if (!url) {
    console.error("`url` field must be specified in data.");
    Deno.exit(0);
}

const headers = new Headers(headersInit ?? {});

try {
    const res = await fetch(url, {
        method,
        headers,
        body: typeof body === "object" && headers.get("content-type") === "application/json"
            ? JSON.stringify(body) 
            : body,
    });

    let resBody;
    if (res.body) {
        if (res.headers.get("content-type")?.startsWith("application/json")) {
            resBody = await res.json();
        } else {
            resBody = await res.text();
        }
    }

    console.log(Deno.inspect({
        url: res.url.toString(),
        redirected: res.redirected,
        status: `${res.status} (${res.statusText})`,
        headers: Object.fromEntries(res.headers.entries()),
        body: resBody,
    }));
} catch(e) {
    console.error(e);
}
