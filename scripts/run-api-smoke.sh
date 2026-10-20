#!/usr/bin/env bash
set -euo pipefail

echo 'No API smoke modules are present in this project (Quality Impact Pack is not enabled).'
echo 'Enable packs.qualityImpact in your qaff config and re-generate to add API smoke tests.'
exit 0
