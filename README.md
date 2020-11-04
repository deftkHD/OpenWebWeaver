LoNetApi is an API wrapper for the JSON API provided by LoNet².
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
Note: Description and identity are used in the LoNet² Web App to identify different tokens

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
- [ ] Add group notifications
- [ ] Edit group notifications
- [ ] Delete group notifications
---
- [x] Read tasks
- [ ] Add tasks
- [ ] Edit tasks
- [ ] Delete tasks
- [ ] Mark tasks as done (Not supported by API)
---
- [ ] Read courselets
- [ ] Manage courselets
---
- [x] Forum quota
- [x] Read forum posts
- [ ] Add forum posts
- [ ] Delete forum posts
- [ ] Forum attachments
---
- [ ] Get wiki
---
- [ ] Resource management
---
- [x] Get file quota
- [x] File storage settings
- [x] Read files
- [x] Download url
- [ ] Download via API
- [ ] Upload
- [ ] Proxies
- [x] Create folders
- [x] Edit file metadata
- [x] Delete files/folders
- [ ] Trash
---
- [x] Read system notifications

## Disclaimer
This project is neither authorized nor endorsed by Cornelsen (LoNet²) or DigiOnline (WebWeaver).