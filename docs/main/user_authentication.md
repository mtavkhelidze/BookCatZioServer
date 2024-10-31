<pre style="background: transparent; padding: 0">
     |\__/,|   (`\
   _.|o o  |_   ) )
---(((---(((-----------
Book Cat ¯\_(ツ)_/¯ Zio
</pre>
### User Authentication
<br/>[Login](#login)
<br/>[Register](#register)
<br/>[Reset Password](#reset-password)
<br/>
<br/>


#### Login

```mermaid
---
title: "POST /auth/user/login"
config:
    fontSize: 20
---
sequenceDiagram
    participant Web Client
    create participant AuthApi as auth.user.Login
    participant AuthCtrl
    participant JwtService
    participant AuthService
    participant UserRepository
%% Happy path
    Note over Web Client, UserRepository: Happy path
    Web Client ->> AuthApi: POST LoginRequest
    activate Web Client
    activate AuthApi
    AuthApi ->> AuthCtrl: CALL login(email, password)
    activate AuthCtrl
    AuthCtrl ->> AuthService: CALL authenticate(creds)
    activate AuthService
    AuthService ->> UserRepository: CALL getUser
    activate UserRepository
    UserRepository ->> AuthService: UserId
    AuthService ->> AuthCtrl: AuthUser
    AuthCtrl ->> JwtService: AuthUser
    activate JwtService
    deactivate JwtService
    destroy JwtService
    JwtService ->> AuthCtrl: JWTToken
    AuthCtrl ->> AuthApi: JWTToken
    AuthApi ->> Web Client: Ok: LoginData
%% JSON Error
    Note over Web Client, UserRepository: Malformed JSON in request
    AuthApi -->> Web Client: UnprocessableEntity: JsonDecodeFailure
%% No DB
    Note over Web Client, UserRepository: Database unavailable
    UserRepository --x AuthService: UserRepoError.DbConnectionGone
    AuthService --x AuthCtrl: AuthError.ServiceUnavailable
    AuthCtrl --x AuthApi: DomainError.CannotCompleteOperation
    AuthApi --x Web Client: BadGateway
%% Invalid credentials
    Note over Web Client, UserRepository: Invalid credentials
    UserRepository --x AuthService: UserRepoError.NoSuchUser
    deactivate UserRepository
    AuthService --x AuthCtrl: AuthError.UserNotFound
    deactivate AuthService
    AuthCtrl --x AuthApi: DomainError.InvalidCredentials
    deactivate AuthCtrl
    AuthApi --x Web Client: Unauthorized
    deactivate AuthApi
    deactivate Web Client
```

#### Reset Password

#### Register

```mermaid
---
title: "POST /auth/user/register"
config:
  fontSize: 18px
---
sequenceDiagram
    Web Client ->> HttpServer: POST RegisterRequest
    activate HttpServer
    Note right of HttpServer: Invalid JSON
    HttpServer -->> Web Client: BadRequest: JsonDecodeFailure
    HttpServer ->> AuthController: CALL register(email, password)
    activate AuthController
    AuthController ->> AuthService: CALL registerUser(creds)
    activate AuthService
    AuthService ->> UserRepository: CALL addUser
    activate UserRepository
    Note right of AuthService: Database unavailable
    AuthService -->> AuthController: AuthError.ServiceUnavailable
    AuthController -->> HttpServer: DomainError.ServiceFailure
    HttpServer -->> Web Client: BadGateway: ServiceUnavailable
    Note right of UserRepository: Duplicate user
    UserRepository -->> AuthService: AuthError.DuplicateEmail
    AuthService -->> AuthController: AuthError.UserAlreadyExists
    AuthController -->> HttpServer: DomainError.UserExists
    HttpServer -->> Web Client: Conflict: UserExists
    note right of UserRepository: User created
    UserRepository ->> AuthService: UserId
    deactivate UserRepository
    AuthService ->> AuthController: AuthUser
    deactivate AuthService
    AuthController ->> HttpServer: JWTToken
    deactivate AuthController
    HttpServer ->> Web Client: RegisterData
    deactivate HttpServer
```

