import Appwrite
import Foundation

func cleanStorage() throws -> Void {
    let group = DispatchGroup()
    group.enter()
    let daysToExpire = ProcessInfo.processInfo.environment["DAYS_TO_EXPIRE"] 
    if(daysToExpire == nil) {
        throw NSError(domain: "DAYS_TO_EXPIRE environment is required", code: 0)
    }
    let endpoint = ProcessInfo.processInfo.environment["APPWRITE_ENDPOINT"] ?? ""
    let project = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_PROJECT_ID"] ?? ""
    
    let key = ProcessInfo.processInfo.environment["APPWRITE_API_KEY"] ?? ""

    let client = Client()
        .setEndpoint(endpoint)
        .setProject(project)
        .setKey(key)

    let storage = Storage(client)

    var deletedFiles = 0
    storage.listFiles(limit: 100, orderType: "DESC") { result in
        switch result {
            case .failure(let error):
                print(error.message)
            case .success(let fileList):
                let timestamp = Calendar.current.date(byAdding: .second, value: -Int(daysToExpire!)!, to: Date())!.timeIntervalSince1970
                print("Timestamp: \(timestamp)")
                for file in fileList.files {
                    let dateCreated = Double(file.dateCreated)
                    print("dateCreated \(dateCreated)")
                    if dateCreated < timestamp {
                        group.enter()
                        storage.deleteFile(fileId: file.id) { result in
                            switch result {
                                case .failure(let error):
                                    print(error)
                                case .success:
                                    deletedFiles += 1
                            }
                            group.leave()
                        }
                    }
                }
        }
        group.leave()
    }
    group.wait()
    print("Total files deleted: \(deletedFiles)")
}

try!
cleanStorage()

