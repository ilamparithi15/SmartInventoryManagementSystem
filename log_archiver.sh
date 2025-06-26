#!/bin/bash

SOURCE_DIR="./logs"
ARCHIVE_DIR="./logs/archive"
LOG_FILE="$ARCHIVE_DIR/log_archiver_activity.log"
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M")
ARCHIVE_NAME="logs_$TIMESTAMP.tar.gz"
ARCHIVE_PATH="$ARCHIVE_DIR/$ARCHIVE_NAME"

mkdir -p "$ARCHIVE_DIR"

LOG_FILES=$(find "$SOURCE_DIR" -maxdepth 1 -type f -name "*.log")

if [ -z "$LOG_FILES" ]; then
  echo "[$(date)] No .log files found in $SOURCE_DIR." >> "$LOG_FILE"
  exit 0
fi

tar -czf "$ARCHIVE_PATH" $LOG_FILES

if [ $? -eq 0 ]; then
  echo "[$(date)] Archived logs to $ARCHIVE_PATH" >> "$LOG_FILE"
else
  echo "[$(date)] ERROR: Failed to archive logs." >> "$LOG_FILE"
fi
