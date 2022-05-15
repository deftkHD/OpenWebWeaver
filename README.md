OpenWebWeaver is an API wrapper for the JSON API provided by websites using WebWeaver®.

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
val apiContext = WebWeaverClient.login(credentials)
```

### Login and create token
```kotlin
val (apiContext, token) = WebWeaverClient.loginCreateToken("<username>", "<password>", "<title>", "<identity>")
```
Note: Title and identity are used in the web app to identify different tokens

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
- [x] Profile details
---
- [ ] Statistics (admin)
- [ ] User management (admin)
---
- [x] Messenger
- [x] Receive quick messages
- [ ] Block users
---
- [x] Read emails
- [x] Write emails
- [x] Email quota
- [x] Email attachments
- [x] Delete emails
- [x] Move emails
- [x] Email signature
- [x] Answer emails
- [x] Forward emails
---
- [x] Get group members
- [x] Send quick messages
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
- [x] Get notes
- [x] Add notes
- [x] Edit notes
- [x] Delete notes
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
---
- [ ] Report users
- [ ] License management
- [ ] Member container join/leave

## Disclaimer
This project is neither authorized nor endorsed by DigiOnline GmbH (WebWeaver®).