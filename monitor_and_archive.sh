#!/bin/bash
INVENTORY_DIR="./inventory"
ARCHIVE_DIR="./archive"
LOG_FILE="./monitor_log.txt"
# Create directories if they don't exist (useful for first run)
mkdir -p "$INVENTORY_DIR"
mkdir -p "$ARCHIVE_DIR"
echo "$(date): Starting inventory monitoring script." | tee -a "$LOG_FILE"
# Find files in inventory directory (excluding directories themselves)
# and move them to the archive directory
if [ "$(ls -A "$INVENTORY_DIR")" ]; then
echo "$(date): Found new files in '$INVENTORY_DIR'." | tee -a "$LOG_FILE"
for file in "$INVENTORY_DIR"/*; do
if [ -f "$file" ]; then # Check if it's a regular file
FILENAME=$(basename "$file")
echo "$(date): Archiving '$FILENAME' to '$ARCHIVE_DIR'." | tee -a
"$LOG_FILE"
mv "$file" "$ARCHIVE_DIR/$FILENAME"
fi
done
echo "$(date): All files moved from '$INVENTORY_DIR'." | tee -a "$LOG_FILE"
else
echo "$(date): No new files found in '$INVENTORY_DIR'." | tee -a "$LOG_FILE"
fi
echo "$(date): Monitoring script finished." | tee -a "$LOG_FILE"

