LoNetApi is an API wrapper for the JSON API provided by lo-net².
It might also work with other instances of WebWeaver, but this is not tested.

## Usage
### One time login
```kotlin
val user = LoNet.login(username, password)
```
### Login with token
```kotlin
val user = LoNet.loginCreateTrust(username, password, description, identity)
```
Note: Description and identity are used in the lo-net² Web App to identify different tokens

The obtained token will be stored in the variable authKey inside the user object.
Re-login using this token:
```kotlin
val user = LoNet.loginToken(username, token)
```

### Revoke token
```kotlin
user.logout(true)
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
---
- [x] Read emails
- [x] Write emails
- [x] Email quota
- [ ] Email attachments
- [x] Delete emails
- [x] Move emails
- [x] Email signature
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
- [ ] Forum attachments
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
- [x] Read system notifications
- [x] Delete system notifications

## Disclaimer
This project is neither authorized nor endorsed by Cornelsen Verlag (lo-net²) or DigiOnline GmbH (WebWeaver).