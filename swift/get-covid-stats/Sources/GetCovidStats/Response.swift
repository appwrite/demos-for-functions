struct Global : Codable {
    let NewConfirmed: Int
    let NewDeaths: Int
    let NewRecovered: Int
}

struct Country : Codable {
    let Country: String
    let CountryCode: String
    let Slug: String
    let NewConfirmed: Int
    let NewDeaths: Int
    let NewRecovered: Int
}

struct Response : Codable {
    let Global: Global
    let Countries: [Country]
}
