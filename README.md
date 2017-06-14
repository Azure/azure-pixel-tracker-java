# Cortana Intelligence Pixel Tracker for Java

Cortana Intelligence Pixel Tracker is a highly scalable service that can ingest thousands of events per second, transform and stream them into multiple applications. This lets you process and analyze the massive amounts of data produced by your connected devices and applications. 

## Overview 

This Java server and library allows for sending events to Azure Event Hub and Azure Data Lake Store. An **event publisher** makes HTTP calls to the service that are picked up and processed. The service acts as an **event consumer**. Processing may involve aggregation, complex computation, and filtering. Processing may also invole distrubtion or stroage of the information in a raw or transformed fashion. This service beginnings with simply archving the raw data into persistant storage. 

The code for receiving events from an HTTP call, and for sending the data to Event Hub is already provided. Please see [Cortana Pixel Tracker Core](./cortana-pixeltracker-core/README.md) for more information about the provided libaries.

The server code uses Spring Boot to wire together the core libaries and expose them as APIs. It is here that advanced custom funcationality can be added. Please see [Cortana Pixel Tracker Server Readme](./cortana-pixeltracker-server/README.md) for more information.

## Disclaimer
©2016 Microsoft Corporation. All rights reserved. This information is provided "as-is" and may change without notice. Microsoft makes no warranties, express or implied, with respect to the information provided here.