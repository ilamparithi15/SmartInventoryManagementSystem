#!/bin/bash

# Set the directory where .log files are located
SOURCE_DIR="./logs"

# Set the directory where archived files will be stored
ARCHIVE_DIR="./logs/archive"

# Define the log file that will record script activity
LOG_FILE="$ARCHIVE_DIR/log_archiver_activity.log"

# Generate a timestamp to uniquely name each archive file
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M")

# Create the name and full path for the archive file
ARCHIVE_NAME="logs_$TIMESTAMP.tar.gz"
ARCHIVE_PATH="$ARCHIVE_DIR/$ARCHIVE_NAME"

# Create the archive directory if it doesn't exist
mkdir -p "$ARCHIVE_DIR"

# Find all .log files in the source directory (non-recursive)
LOG_FILES=$(find "$SOURCE_DIR" -maxdepth 1 -type f -name "*.log")

# If no .log files are found, log that information and exit
if [ -z "$LOG_FILES" ]; then
  echo "[$(date)] No .log files found in $SOURCE_DIR." >> "$LOG_FILE"
  exit 0
fi

# Compress all found .log files into a timestamped archive
tar -czf "$ARCHIVE_PATH" $LOG_FILES

# Check if the archive command was successful
if [ $? -eq 0 ]; then
  # Log success message with timestamp and archive path
  echo "[$(date)] Archived logs to $ARCHIVE_PATH" >> "$LOG_FILE"
else
  # Log failure message if the archiving failed
  echo "[$(date)] ERROR: Failed to archive logs." >> "$LOG_FILE"
fi

