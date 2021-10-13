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

    storage.listFiles(limit: 100, orderType: "desc") { result in
        switch result {
            case .failure(let error):
                print(error.message)
            case .success(let fileList):
                var deletedFiles = 0
                let timestamp = Calendar.current.date(byAdding: .day, value: -Int(daysToExpire!)!, to: Date())!.timeIntervalSince1970
                for file in fileList.files {
                    let dateCreated = Double(file.dateCreated)
                    if dateCreated < timestamp {
                        storage.deleteFile(fileId: file.id) { result in
                            switch result {
                                case .failure(let error):
                                    print(error)
                                case .success:
                                    deletedFiles += 1
                            }
                        }
                    }
                    //print total files deleted
                    print("Total files deleted: \(deletedFiles)")

                }
        }
    }
    group.leave()

}

try!
cleanStorage()

