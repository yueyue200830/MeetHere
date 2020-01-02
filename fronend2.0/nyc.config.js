module.exports = {
  'check-coverage': true,
  'per-file': true,
  'skip-full': true,
  'sourceMap': false,
  "instrucment": false,
  all: true,
  include: [
    'src/**/*.{js,vue}',
    'src/views/*.vue'
  ],
  exclude: [
    'src/*.js',
    '**/index.js'
  ],
  extension: [
    '.js',
    '.vue'
  ]
};
