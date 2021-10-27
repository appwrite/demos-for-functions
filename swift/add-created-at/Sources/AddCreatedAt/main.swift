import Appwrite
import Foundation

let createdAtKey = ProcessInfo.processInfo.environment["CREATED_AT_KEY"] ?? "createdAt"
let eventString = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_EVENT_DATA"] ?? ""
let eventData = eventString.data(using: .utf8)

if (!JSONSerialization.isValidJSONObject(eventData!)) {
    print("[ERROR] Event data not specified or invalid.")
    throw NSError(domain: "Event data not specified or invalid.", code: 0)
}

let eventJson: [String: String] = try! JSONSerialization.jsonObject(with: eventData!) as! [String: String]

let client = Client()
        .setEndpoint(ProcessInfo.processInfo.environment["APPWRITE_ENDPOINT"] ?? "")
        .setProject(ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_PROJECT_ID"] ?? "")
        .setKey(ProcessInfo.processInfo.environment["APPWRITE_API_KEY"] ?? "")

let database = Database(client)
let group = DispatchGroup()

var collection: Collection? = nil

group.enter()
database.getCollection(collectionId: eventJson["$collection"]!) { result in
    switch result {
    case .failure(let err):
        print("[ERROR] \(err.message)")
        exit(1)
    case .success(let collectionResult):
        collection = collectionResult
    }
    group.leave()
}

group.wait()

if (collection!.rules.filter { rule in rule.key == createdAtKey }.isEmpty) {
    print("[ERROR] Rule with key \"\(createdAtKey)\" not found from collection \"\(collection!.id)\"")
    exit(1)
}

group.enter()
database.updateDocument(collectionId: collection!.id, documentId: eventJson["$id"]!, data: [createdAtKey: Int(Date().timeIntervalSince1970)]) { result in
    switch result {
    case .failure(let err):
        print("[ERROR] \(err.message)")
        exit(1)
    case .success(let document):
        print("Successfully set collection \(collection!.id) document \(document.id) \"\(createdAtKey)\" attribute to current datetime.")
    }
    group.leave()
}

group.wait()
