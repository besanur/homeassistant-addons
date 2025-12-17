# Changelog

All notable changes to this project will be documented in this file.

## [1.2.1] - 2025-02-08
### What's Changed
- **Changes**
  - new Logo
  - change java version to 25
  - change base image from debian to alpine 3.23

- **Bumped Dependencies:**
  - Spring Boot 4.0.0
  - jsoup 1.21.2

---

## [1.2.1] - 2025-02-08
### What's Changed
- **Fixed:** Changed API URL to `ezanvakti.emushaf.net`
- **Bumped Dependencies:**
  - `home-assistant/builder` from 2023.09.0 to 2024.08.2 ([#42](https://github.com/besanur/homeassistant-addons/pull/42), [#45](https://github.com/besanur/homeassistant-addons/pull/45))
  - `actions/checkout` from 4.1.0 to 4.2.2 ([#39](https://github.com/besanur/homeassistant-addons/pull/39), [#52](https://github.com/besanur/homeassistant-addons/pull/52))
  - `frenck/action-addon-linter` from 2.13 to 2.18 ([#40](https://github.com/besanur/homeassistant-addons/pull/40), [#51](https://github.com/besanur/homeassistant-addons/pull/51))
  - `docker/login-action` from 3.0.0 to 3.3.0 ([#46](https://github.com/besanur/homeassistant-addons/pull/46))

**Full Changelog:** [1.2.0...1.2.1](https://github.com/besanur/homeassistant-addons/compare/1.2.0...1.2.1)

---

## [1.2.0] - 2023-10-09
### What's Changed
- **Added:** Daily content (see `README.md`)
- **Fixed:** Parse issue of prayer times
- **Changed:** Use in-memory storage instead of H2 database

---

## [1.1.1] - 2023-08-20
### What's Changed
- **Fixed:** Parse error
- [Full Changelog](https://github.com/besanur/homeassistant-addons/compare/1.1.0...1.1.1)

---

## [1.1.0] - 2022-11-07
### What's Changed
- **New:** Introduce new version with H2 DB by [@besanur](https://github.com/besanur) in [#9](https://github.com/besanur/homeassistant-addons/pull/9)
- **Updated:**
  - Bump `actions/checkout` from 3.0.2 to 3.1.0 by [@dependabot](https://github.com/dependabot) in [#7](https://github.com/besanur/homeassistant-addons/pull/7)
  - Bump `frenck/action-addon-linter` from 2.8 to 2.10 by [@dependabot](https://github.com/dependabot) in [#5](https://github.com/besanur/homeassistant-addons/pull/5)
- **Fixed:**
  - Fix build by [@besanur](https://github.com/besanur) in [#10](https://github.com/besanur/homeassistant-addons/pull/10)
  - Revert GitHub action builder by [@besanur](https://github.com/besanur) in [#12](https://github.com/besanur/homeassistant-addons/pull/12)
  - ARM machines need `launchMechanism` set to `vfork` by [@besanur](https://github.com/besanur) in [#13](https://github.com/besanur/homeassistant-addons/pull/13)
  - Revert ARMv7 support by [@besanur](https://github.com/besanur) in [#14](https://github.com/besanur/homeassistant-addons/pull/14)

---

## [1.0.0] - 2022-05-23
### Initial Release
- First release of the add-on
