<pre style="background: transparent; padding: 0">
     |\__/,|   (`\
   _.|o o  |_   ) )
---(((---(((-----------
Book Cat ¯\_(ツ)_/¯ Zio
</pre>

### Error Handling Pipeline

```mermaid
---
title: "Error Pipeline"
config:
    fontSize: 20
---
flowchart
    subgraph server
        WebClient[Web Client]
    end
    subgraph api
        Endpoint --> toResponse[[ToResponse]] -- " Response(StatusCode, jsonBody[CtrlError]) " --> WebClient
    end
    subgraph http.routes
        direction TB
        Route --> encodeJson[[ToJson]] -- " Json(cause: CtrlError, messages: List[String]) " --> Endpoint
    end
    subgraph control
        ctrl[OneCtrl] --> toCtrlError[[ToCtrlError]] -- " CtrlError(message: List[String]) " --> Route

    end
    subgraph services
        service[Service One] -- ServiceError --> ctrl[OneCtrl]
    end
```
