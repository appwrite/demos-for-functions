import Appwrite
import Foundation

let group = DispatchGroup()

let project = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_PROJECT_ID"] ?? ''

let client = Client()
    .setEndpoint("https://[HOSTNAME_OR_IP]/v1") // Your API Endpoint
        .setProject(project) // Your project ID
        .setKey("919c2d18fb5d4...a2ae413da83346ad2")

let storage = Storage(client)

group.enter()
storage.listFiles(orderType: "desc", limit: 100) { result in
    switch result {
        case .failure(let error):
            throw error
        case .success(let fileList):
            print(files)
    }
    group.leave()
}
