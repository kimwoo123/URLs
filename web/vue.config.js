module.exports = {
  pluginOptions: {
    quasar: {
      importStrategy: 'kebab',
      rtlSupport: false
    }
  },
  transpileDependencies: [
    'quasar'
  ],
  lintOnSave: true,
  filenameHashing: false,
  output: {
    filename: '[name].[hash].bundle.js'
  }
}
