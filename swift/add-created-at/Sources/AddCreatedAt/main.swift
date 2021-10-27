import Appwrite
import Foundation

let createdAtKey = ProcessInfo.processInfo.environment["CREATED_AT_KEY"] ?? "createdAt"
let eventString = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_EVENT_DATA"] ?? "{}"
let eventData = eventString.data(using: .utf8)!

guard let json = try JSONSerialization.jsonObject(with: eventData) as? [String: Any] else {
    print("[ERROR] Unable to parse APPWRITE_FUNCTION_EVENT_DATA")
    exit(1)
}

guard let documentId = json["$id"] as? String else {
    print("[ERROR] Unable to parse document ID")
    exit(1)
}

guard let collectionId = json["$collection"] as? String else {
    print("[ERROR] Unable to parse collection ID")
    exit(1)
}

if (json[createdAtKey] == nil) {
    print("[ERROR] Collection \"\(collectionId)\" has no key \"\(createdAtKey)\" for setting created at datetime.")
    exit(1)
}

let client = Client()
        .setEndpoint(ProcessInfo.processInfo.environment["APPWRITE_ENDPOINT"] ?? "")
        .setProject(ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_PROJECT_ID"] ?? "")
        .setKey(ProcessInfo.processInfo.environment["APPWRITE_API_KEY"] ?? "")

let database = Database(client)
let group = DispatchGroup()

group.enter()
database.updateDocument(collectionId: collectionId, documentId: documentId, data: [createdAtKey: Int(Date().timeIntervalSince1970)]) { result in
    switch result {
    case .failure(let err):
        print("[ERROR] \(err.message)")
        exit(1)
    case .success(let document):
        print("Successfully set collection \(collectionId) document \(document.id) \"\(createdAtKey)\" attribute to current datetime.")
    }
    group.leave()
}

group.wait()
