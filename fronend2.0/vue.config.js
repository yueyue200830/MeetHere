
module.exports = {
  devServer: {
    proxy: {
      '/app': {
        target: 'http://localhost:8081/',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/app': ''
        }
      }
    }
  }
}
