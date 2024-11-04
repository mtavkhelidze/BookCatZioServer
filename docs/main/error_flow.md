<pre style="background: transparent; padding: 0">
     |\__/,|   (`\
   _.|o o  |_   ) )
---(((---(((-----------
Book Cat ¯\_(ツ)_/¯ Zio
</pre>
### Error Flow
```mermaid
---
title: "Error Flow"
config:
    fontSize: 20
---
flowchart
    ServiceOne -- ServiceOneError --> ctrl
    ServiceTwo -- ServiceTwoError --> ctrl
    Service... -- Service . . . Error --> ctrl
    ctrl(Controller) -- " ControlError(message) " --> toHttpError[[toHttpError]]
    toHttpError -- " HttpError(message, cause) " --> json[[toJson]]
    json -- " HttpError(StatusCode, jsonBody) " --> Client
```
