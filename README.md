# JoinMessages

A lightweight Kotlin plugin for Paper servers that adds configurable join and leave messages with full hex color support.

## Features

- Custom join and leave messages
- Separate first-join message
- Hex color support (`&#RRGGBB`) and legacy color codes (`&a`, `&c`, etc.)
- Optional join/leave sounds
- In-game config reload
- Folia support

## Installation

1. Download the latest jar from [Releases](https://github.com/Bytewave1/JoinMessages/releases)
2. Drop it into your `plugins/` folder
3. Restart the server
4. Edit `plugins/JoinMessages/config.yml` to your liking

## Default Config

```yml
join-message: "&#9F9F9F[&#03FF00+&#9F9F9F] &#9F9F9F%player%"
leave-message: "&#9F9F9F[&#FF0000-&#9F9F9F] &#9F9F9F%player%"

first-join:
  enabled: true
  message: "&#9F9F9F[&#03FF00+&#9F9F9F] &#FFAA00%player% &#9F9F9Fjoined for the first time!"

sounds:
  join:
    enabled: false
    sound: "ENTITY_PLAYER_LEVELUP"
    volume: 1.0
    pitch: 1.0
  leave:
    enabled: false
    sound: "ENTITY_ENDERMAN_TELEPORT"
    volume: 1.0
    pitch: 1.0
```

## Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/joinmessages reload` | `joinmessages.reload` | Reloads the config |

Alias: `/jm reload`

## Placeholders

| Placeholder | Description |
|-------------|-------------|
| `%player%` | Player name |

## Building

```bash
git clone https://github.com/Bytewave1/JoinMessages.git
cd JoinMessages
./gradlew build
```

The jar will be in `build/libs/`.

## Requirements

- Paper 1.21+ (or Folia)
- Java 21+
