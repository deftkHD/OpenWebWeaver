LoNetApi is an API wrapper for the JSON API provided by lo-net².
It might also work with other instances of WebWeaver, but this is not tested.

## Getting started

### Create credentials
If no token was created:
```kotlin
val credentials = Credentials.fromPassword("<username>", "<password>")
```
Or if you already have a token:
```kotlin
val credentials = Credentials.fromToken("<username>", "<token>")
```

### Simple login
```kotlin
val apiContext = LoNetClient.login(credentials)
```

### Login and create token
```kotlin
val (apiContext, token) = LoNetClient.loginCreateToken("<username>", "<password>", "<title>", "<identity>")
```
Note: Title and identity are used in the lo-net² Web App to identify different tokens

### Obtain user object
```kotlin
val user = apiContext.getUser()
```

### Obtain groups
```kotlin
val groups = user.getGroups()
```

### Obtain request context
Each request made (except login) needs a request context. Classes implementing the IOperatingScope interface (IUser and IGroup) are able to create a request context from the IApiContext (obtained during login).
```kotlin
val requestContext = user.getRequestContext(apiContext)
```
```kotlin
val requestContext = group.getRequestContext(apiContext)
```

### Revoke token (and logout)
```kotlin
user.logoutDestroyToken("<token>", requestContext)
```

## Status
- [x] Simple login
- [x] Token login
- [x] Auto login urls
- [ ] Change user password
- [ ] Profile details
---
- [ ] Statistics (admin)
- [ ] User management (admin)
---
- [ ] Messenger
- [x] Receive quick messages
---
- [x] Read emails
- [x] Write emails
- [x] Email quota
- [x] Email attachments
- [x] Delete emails
- [x] Move emails
- [x] Email signature
- [ ] Answer emails
---
- [x] Get group members
- [ ] Send quick messages
---
- [x] Get contacts
- [x] Add contacts
- [x] Edit contacts
- [x] Delete contacts
---
- [x] Get calendar
- [x] Add appointments
- [x] Edit appointments
- [x] Delete appointments
---
- [ ] Get notes
- [ ] Add notes
- [ ] Edit notes
- [ ] Delete notes
---
- [x] Read group notifications
- [x] Add group notifications
- [x] Edit group notifications
- [x] Delete group notifications
---
- [x] Read tasks
- [x] Add tasks
- [x] Edit tasks
- [x] Delete tasks
- [ ] Mark tasks as done (Not supported by API)
---
- [x] Read courselets
- [ ] Parse courselets configuration
- [ ] Courselet templates
- [ ] Import courselets
- [x] Manage/edit courselets (Only partly supported by API)
---
- [x] Forum quota
- [x] Read forum posts
- [x] Add forum posts
- [x] Delete forum posts
---
- [x] Get wiki
---
- [ ] Resource management
---
- [x] Get file quota
- [x] File storage settings
- [x] Read files
- [x] Download url
- [x] Download via API
- [x] Upload
- [ ] Proxies
- [x] Create folders
- [x] Edit file metadata
- [x] Delete files/folders
- [x] Trash
---
- [x] Session files
- [x] Session files and file storage
---
- [x] Read system notifications
- [x] Delete system notifications

## Disclaimer
This project is neither authorized nor endorsed by Cornelsen Verlag (lo-net²) or DigiOnline GmbH (WebWeaver).