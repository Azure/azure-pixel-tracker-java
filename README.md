# Cortana Intelligence Pixel Tracker for Java

Cortana Intelligence Pixel Tracker is a highly scalable service that can ingest thousands of events per second, transform and stream them into multiple applications. This lets you process and analyze the massive amounts of data produced by your connected devices and applications. 

## Overview 

This Java server and library allows for sending events to Azure Event Hub and Azure Data Lake Store. An **event publisher** makes HTTP calls to the service that are picked up and processed. The service acts as an **event consumer**. Processing may involve aggregation, complex computation, and filtering. Processing may also invole distrubtion or stroage of the information in a raw or transformed fashion. This service beginnings with simply archving the raw data into persistant storage. 

The code for receiving events from an HTTP call, and for sending the data to Event Hub is already provided. Please see [Cortana Pixel Tracker Core Readme](./cortana-pixeltracker-core/README.md) for more information about the provided libaries.

The server code uses Spring Boot to wire together the core libaries and expose them as APIs. It is here that advanced custom funcationality can be added. Please see [Cortana Pixel Tracker Server Readme](./cortana-pixeltracker-server/README.md) for more information.


# Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## Disclaimer
©2016 Microsoft Corporation. All rights reserved. This information is provided "as-is" and may change without notice. Microsoft makes no warranties, express or implied, with respect to the information provided here.