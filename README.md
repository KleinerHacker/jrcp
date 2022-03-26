# JRCP - Java Rest Client Proxy

## Overview

A framework to create a HTTP client based on a given Annotation Framework. It creates proxies for defined interfaces to use like RMI proxies.

## How to use

### Maven Import

TODO

### Use in code

To create a simple JRCP client use always the builder:

```Java
final var client = JRCPClient.createBuilder("http://localhost:8080/rest/api")
   .withApiInterface(Customer.class, Carret.class)
   .withAnnotationProvider(SpringAnnotationProvider.INSTANCE)
   .withStandardContentProviders()
   .build();
```

Now you can use this client to contact the server via the API interface proxies:

```Java
final var proxy = client.getProxy(Customer.class);
proxy.addCustomer(...);
proxy.getCustomers();
...
```

## Annotation Supports

Currently there is one annotation support:
* **Spring** - for Spring Boot Annotations - Maven Artefact (TODO)

## Custom Extensions

### Annotation Provider

To add a custom annotation provider implmements the `AnnotationProvider` interface.

TODO: Example

### Content Provider

To add a custom content provider implements one of this interfaces:
* `StringContentProvider` - Creates a string based entity (for body), used for e. g. JSON
* `BinaryContentProvider` - Creates a binary based entity (for body), used for e. g. Java Object Serialization

TODO: Example
