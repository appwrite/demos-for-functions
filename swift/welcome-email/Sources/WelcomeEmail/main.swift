import AsyncHTTPClient
import Foundation

func sendSimpleMessage(name: String, email: String) throws  {
    let message = "Welcome \(name)!"
    let targetURL = "https://api.mailgun.net/v3/\(MAILGUN_DOMAIN)/messages"
    let params =  [
        "from" : "Excited User <hello@appwrite.io>",
        "to" : email,
        "subject" : "hello",
        "text" : message
    ]

    var request: HTTPClient.Request
    do {
        request = try HTTPClient.Request(
            url: targetURL,
            method: .RAW(value: "POST")
        )

        let auth = "api:\(MAILGUN_API_KEY)".data(using: String.Encoding.utf8)!.base64EncodedString()
        request.headers.add(name: "Content-Type", value: "multipart/form-data")
        request.headers.add(name: "Authorization", value: "Basic \(auth)")
        
        buildMultipart(&request, with: params)

        httpClient.execute(request: request).whenComplete { result in
            switch result {
            case .failure(let error):
                print("Error: \(error)")
            case .success(let response):
                if response.status == .ok {
                    print("Message sent!")
                } else {
                    print("Error: \(response.status)")
                }
            }

            group.leave();
        }

    } catch let error {
        print(error)
        return
    }
}

let MAILGUN_DOMAIN = ProcessInfo.processInfo.environment["MAILGUN_DOMAIN"]!
let MAILGUN_API_KEY = ProcessInfo.processInfo.environment["MAILGUN_API_KEY"]!
let APPWRITE_FUNCTION_EVENT_DATA = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_EVENT_DATA"] ?? "{}"

let httpClient = HTTPClient(eventLoopGroupProvider: .createNew)
let group = DispatchGroup()

do {
    let data = Data(APPWRITE_FUNCTION_EVENT_DATA.utf8)

    guard let json = try JSONSerialization.jsonObject(with: data) as? [String: Any] else {
        throw "Unable to parse APPWRITE_FUNCTION_EVENT_DATA"
    }
    
    guard let name = json["name"] as? String else {
        throw "Unable to parse name"
    }
    
    guard let email = json["email"] as? String else {
        throw "Unable to parse email"
    }

    group.enter()
    try sendSimpleMessage(name: name, email: email)
    group.wait()

} catch let error {
    print(error)
}

