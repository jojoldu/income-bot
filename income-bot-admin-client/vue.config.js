const webpack = require('webpack');
const isProd = process.env.NODE_ENV === "production";

module.exports = {
  devServer: {
    proxy: { // proxyTable 설정
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  },
  configureWebpack: {
    // Set up all the aliases we use in our app.
    // devtool: 'eval-source-map',
    plugins: [
      new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 6
      })
    ]
  },
  pwa: {
    "name": 'Vue Argon Dashboard',
    "themeColor": '#172b4d',
    "msTileColor": '#172b4d',
    "appleMobileWebAppCapable": 'yes',
    "appleMobileWebAppStatusBarStyle": '#172b4d'
  },
  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== 'production'
  }
};
