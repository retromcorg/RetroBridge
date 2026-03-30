# RetroBridge

RetroBridge is a compatibility bridge plugin for Project Poseidon servers.

It provides a stable abstraction layer for common server features and selects an active provider based on what plugins are installed and how the server is configured.

Current modules:

- economy
- permissions
- auth
- whois
- vanish
- fakequit

## Supported Providers

### Economy

- Essentials
- Fundamentals
- zCore
- Dummy fallback

### Permissions

- Dummy fallback

### Auth

- AuthMe
- Dummy fallback

### Whois

- GeoIPTools
- Dummy fallback

### Vanish

- Fundamentals
- zCore
- Dummy fallback

### FakeQuit

- Fundamentals
- Dummy fallback

## How It Works

RetroBridge registers providers for each module and selects one active provider per module.

Selection rules:

1. If `preferred-provider.value` is set to a specific provider name, RetroBridge tries that provider first.
2. If `preferred-provider.value` is `AUTO`, RetroBridge uses the first available provider in registration order.
3. If a specific preferred provider is unavailable and `allow-fallback.value` is `true`, RetroBridge falls back to the first other available provider.
4. If a specific preferred provider is unavailable and `allow-fallback.value` is `false`, the module has no active provider.

Provider availability is determined by whether the backing plugin is enabled.

## Command

- `/retrobridge` - The command shows the active provider for each module, including the owner plugin and whether the active provider was registered as `BUILTIN` or `RUNTIME`.

## Documentation

- [Configuration](docs/CONFIG.md)
- [Plugin Integration](docs/INTEGRATION.md)

## Notes

- Dummy providers are intentional fallbacks for modules with no real active integration.
- Some bridges depend on online players for certain operations.

## Building

Compile:

```bash
mvn -q -DskipTests compile
```

Package:

```bash
mvn package
```
