# Guardium Reporting Wrapper for Cognos
The Guardium Reporting Wrapper for Cognos is a web service implemented as a J2EE servlet that exposes Guardium data in a format that can be consumed and then reported on by Cognos. It works by interfacing with Guardium's REST API, retrieving report data as JSON and then translating it into the XML format that Cognos requires for it's XML datasource type.

Using the service, you can configure Cognos to retrieve and report on Guardium data without exporting the data to a reporting database first.

## Dependencies
The service requires that the [gson library](https://code.google.com/p/google-gson/) be present in the build path during compilation and the web application server's libraries during execution.

## License
The software is released under the MIT license.

## About the Author
The Guardium Reporting Wrapper for Cognos was developed by John Haldeman as a side project. John currently works as the Security Practice Lead at Information Insights LLC. If you would like to contribute to the project, or have any questions about it, you can contact him at john.haldeman@infoinsightsllc.com
