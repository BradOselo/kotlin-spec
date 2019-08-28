source directories.sh
source settings.sh

init_settings "html"

export PROJECT_DIR

cd ${PROJECT_DIR}/docs/src/md

gpp -H ./index.md | pandoc \
--filter ${FILTERS_DIR}/processTodoFilter.sh \
--filter ${FILTERS_DIR}/markSentencesFilter.sh \
--filter ${FILTERS_DIR}/copyPasteFilter.sh \
--filter ${FILTERS_DIR}/inlineDiagrams.sh \
--filter ${FILTERS_DIR}/inlineCodeIndenter.sh \
--filter ${FILTERS_DIR}/mathCleanUpFilter.sh \
${PREAMBLE_OPTIONS} \
${COMMON_PANDOC_OPTIONS} \
${TOC_PANDOC_OPTIONS} \
${HTML_ASSETS_OPTIONS} \
-o ${BUILD_DIR}/html/kotlin-spec.html
