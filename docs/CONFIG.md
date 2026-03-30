# Configuration

RetroBridge writes its config automatically on first startup.

## Top-Level Keys

### `config-version`

Internal config version written by the plugin.

### `settings.debug.value`

Enables additional debug logging.

When `true`, RetroBridge logs:

- provider registration
- module refreshes
- selection context
- plugin enable and disable refresh events

Default:

```yml
settings:
  debug:
    value: false
```

### `settings.hooks.refresh-on-plugin-events.value`

Controls whether RetroBridge refreshes module selection when plugins are enabled or disabled.

Default:

```yml
settings:
  hooks:
    refresh-on-plugin-events:
      value: true
```

## Per-Module Settings

Each module has the same config shape under:

`settings.hooks.modules.<module>`

Available modules:

- `economy`
- `permissions`
- `auth`
- `whois`
- `vanish`
- `fakequit`

### `preferred-provider.value`

Controls which provider RetroBridge should prefer for that module.

Accepted values:

- `AUTO`
- a concrete provider name such as `Essentials`, `Fundamentals`, `ZCore`, `AuthMe`, or `GeoIPTools`

Behavior:

- `AUTO`: use the first available provider in registration order
- specific provider name: try that provider first

Example:

```yml
settings:
  hooks:
    modules:
      economy:
        preferred-provider:
          value: Fundamentals
```

### `allow-fallback.value`

Controls what happens if a specific preferred provider is unavailable.

Behavior:

- `true`: use the first other available provider
- `false`: leave the module without an active provider

If `preferred-provider.value` is `AUTO`, this setting does not materially affect selection.

Example:

```yml
settings:
  hooks:
    modules:
      vanish:
        preferred-provider:
          value: ZCore
        allow-fallback:
          value: true
```

## Example Config

```yml
config-version: 1

settings:
  debug:
    value: false
  hooks:
    refresh-on-plugin-events:
      value: true
    modules:
      economy:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
      permissions:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
      auth:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
      whois:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
      vanish:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
      fakequit:
        preferred-provider:
          value: AUTO
        allow-fallback:
          value: true
```
