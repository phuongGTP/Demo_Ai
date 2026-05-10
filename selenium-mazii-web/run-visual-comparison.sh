#!/bin/bash

# Visual Regression Test Comparison Script
# Runs BackstopJS comparison and generates HTML report

set -e

cd "$(dirname "$0")"

echo "🎨 Visual Regression Testing — Comparison Phase"
echo "==============================================="
echo ""

# Step 1: Verify images exist
echo "📸 Checking images..."
REFERENCE_COUNT=$(ls backstop_data/bitmaps_reference/*.png 2>/dev/null | wc -l)
TEST_COUNT=$(ls backstop_data/bitmaps_test/*.png 2>/dev/null | wc -l)

echo "   Reference images: $REFERENCE_COUNT"
echo "   Test images: $TEST_COUNT"
echo ""

if [ $REFERENCE_COUNT -eq 0 ]; then
    echo "❌ ERROR: No reference images found!"
    echo "   Run: npx backstop reference"
    exit 1
fi

if [ $TEST_COUNT -eq 0 ]; then
    echo "❌ ERROR: No test images found!"
    echo "   Run: mvn test -Dtest=VisualRegressionTest"
    exit 1
fi

# Step 2: Run comparison
echo "🔍 Running BackstopJS comparison..."
npx backstop test --config=backstop.json 2>&1 | tail -20

# Step 3: Check results
echo ""
echo "📊 Comparison completed!"
echo ""
echo "Next steps:"
echo "   1. View HTML report: backstop openReport"
echo "   2. Or open: backstop_data/html_report/index.html"
echo "   3. Review pixel differences (green = pass, red = fail)"
echo "   4. If intentional: backstop approve"
echo ""
