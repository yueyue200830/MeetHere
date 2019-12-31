
module.exports = {
  devServer: {
    proxy: {
      '/app': {
        target: 'http://49.235.124.124:8081/',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/app': ''
        }
      }
    }
  }
}
