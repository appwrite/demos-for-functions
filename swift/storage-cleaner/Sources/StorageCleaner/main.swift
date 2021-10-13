import Appwrite
import Foundation

func cleanStorage() {
    let group = DispatchGroup()
    group.enter()
    let daysToExpire = ProcessInfo.processInfo.environment["DAYS_TO_EXPIRE"] 
    if(daysToExpire == nil) {
        throw Error("DAYS_TO_EXPIRE environment is required")
    }

    let endpoint = ProcessInfo.processInfo.environment["APPWRITE_ENDPOINT"] ?? ''

    let project = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_PROJECT_ID"] ?? ''
    
    let key = ProcessInfo.processInfo.environment["APPWRITE_API_KEY"] ?? ''

    let client = Client()
        .setEndpoint(endpoint)
        .setProject(project)
        .setKey(key)

    let storage = Storage(client)

    storage.listFiles(orderType: "desc", limit: 100) { result in
        switch result {
            case .failure(let error):
                throw error
            case .success(let files):
                var deletedFiles = 0
                for file in files {
                    let date = Date(timeIntervalSince1970: file.dateCreated)

                    storage.deleteFile(fileId: file.id) { result in
                        switch result {
                            case .failure(let error):
                                print(error)
                            case .success:
                                deletedFiles += 1
                        }
                    }
                    //print total files deleted
                    print("Total files deleted: \(deletedFiles)")

                }
        }
    }
    group.leave()

}

cleanStorage()

