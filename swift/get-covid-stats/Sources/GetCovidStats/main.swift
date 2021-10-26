import AsyncHTTPClient
import Foundation
import NIOFoundationCompat

let countryIdentifier = ProcessInfo.processInfo.environment["APPWRITE_FUNCTION_DATA"] ?? ""

let httpClient = HTTPClient(eventLoopGroupProvider: .createNew)
let group = DispatchGroup()

group.enter()
httpClient.get(url: "https://api.covid19api.com/summary").whenComplete { result in
    switch result {
    case .failure(let err):
        print("[ERROR] \(err)")
    case .success(let response):
        if (response.status != .ok) {
            print("[ERROR] \(response.status)")
        } else {
            let data: Response = try! JSONDecoder().decode(
                    Response.self, from: response.body!
            )

            let resultCountry = data.Countries.filter { resultCountry in
                [
                    resultCountry.CountryCode.lowercased(),
                    resultCountry.Country.lowercased(),
                    resultCountry.Slug.lowercased()
                ].contains(countryIdentifier.lowercased())
            }.first

            // Variables that will be shown in output
            let country = resultCountry?.Country ?? "the world"
            let confirmedCasesToday = resultCountry?.NewConfirmed ?? data.Global.NewConfirmed
            let deathsToday = resultCountry?.NewDeaths ?? data.Global.NewDeaths
            let recoveredToday = resultCountry?.NewRecovered ?? data.Global.NewRecovered

            print("""
                  COVID-19 stats for \(country):
                  Confirmed cases today: \(confirmedCasesToday)
                  Recovered today: \(recoveredToday)
                  Deaths today: \(deathsToday)
                  """)
        }
    }

    group.leave()
}
group.wait()
