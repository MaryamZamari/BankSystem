# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - 2026-01-06

### Added
- Created `FileIOUtil` singleton to handle cross-service file operations.
- Implemented `Currency` object to replace the limited `AccountType` enum.
- Added generic `saveJson` and `loadJson` methods using `<T>` for reusability.

### Changed
- Refactored `AccountService` and `ClientService` to delegate IO tasks to `FileIOUtil`.
- Updated `ScannerWrapperUtil` to use recursive input validation for better UX.
- Standardized `DocFile` usage across all data saving and loading interfaces.