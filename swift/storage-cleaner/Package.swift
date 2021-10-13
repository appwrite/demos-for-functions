// swift-tools-version:5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "StorageCleaner",
    dependencies: [
        // Dependencies declare other packages that this package depends on.
        .package(url: "https://github.com/appwrite/sdk-for-swift", .branch("dev"))
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .executableTarget(
            name: "StorageCleaner",
            dependencies: [
                .product(name: "Appwrite", package: "sdk-for-swift"),
            ]),
        .testTarget(
            name: "StorageCleanerTests",
            dependencies: ["StorageCleaner"]),
    ],
    swiftLanguageVersions: [.v5]
)
