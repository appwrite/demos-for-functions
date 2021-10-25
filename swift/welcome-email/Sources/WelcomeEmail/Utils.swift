import AsyncHTTPClient
import NIO

let DASHDASH = "--"
let CRLF = "\r\n"
let boundaryChars = "abcdefghijklmnopqrstuvwxyz1234567890"

func randomBoundary() -> String {
    var string = ""
    for _ in 0..<16 {
        string.append(boundaryChars.randomElement()!)
    }
    return string
}

func buildMultipart(
    _ request: inout HTTPClient.Request,
    with params: [String: Any?] = [:]
) {
    func addPart(name: String, value: Any) {
        bodyBuffer.writeString(DASHDASH)
        bodyBuffer.writeString(boundary)
        bodyBuffer.writeString(CRLF)
        bodyBuffer.writeString("Content-Disposition: form-data; name=\"\(name)\"")

        if let file = value as? File {
            bodyBuffer.writeString("; filename=\"\(file.name)\"")
            bodyBuffer.writeString(CRLF)
            bodyBuffer.writeString("Content-Length: \(bodyBuffer.readableBytes)")
            bodyBuffer.writeString(CRLF+CRLF)
            bodyBuffer.writeBuffer(&file.buffer)
            bodyBuffer.writeString(CRLF)
            return
        }

        let string = String(describing: value)
        bodyBuffer.writeString(CRLF)
        bodyBuffer.writeString("Content-Length: \(string.count)")
        bodyBuffer.writeString(CRLF+CRLF)
        bodyBuffer.writeString(string)
        bodyBuffer.writeString(CRLF)
    }

    let boundary = randomBoundary()
    var bodyBuffer = ByteBuffer()

    for (key, value) in params {
        switch key {
        case "file":
            addPart(name: key, value: value!)
        default:
            if let list = value as? [Any] {
                for listValue in list {
                    addPart(name: "\(key)[]", value: listValue)
                }
                continue
            }
            addPart(name: key, value: value!)
        }
    }

    bodyBuffer.writeString(DASHDASH)
    bodyBuffer.writeString(boundary)
    bodyBuffer.writeString(DASHDASH)
    bodyBuffer.writeString(CRLF)

    request.headers.remove(name: "content-type")
    request.headers.add(name: "Content-Length", value: bodyBuffer.readableBytes.description)
    request.headers.add(name: "Content-Type", value: "multipart/form-data;boundary=\"\(boundary)\"")
    request.body = .byteBuffer(bodyBuffer)
}

extension String: Error {}