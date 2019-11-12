const path = require('path');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

module.exports = {
  target: 'node',
  entry: {
    changeHeaterStatus: path.resolve(__dirname, './changeHeaterStatus/index.ts'),
    registerTemperatureSensor: path.resolve(__dirname, './registerTemperatureSensor/index.ts')
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      }
    ]
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
    plugins: [
      new TsconfigPathsPlugin()
    ],
    alias: {
      shared: path.resolve(__dirname, '../shared'),
      contract: path.resolve(__dirname, '../contract')
    }
  },
  optimization: {
    minimize: false
  },
  devtool: 'source-map',
  output: {
    filename: '[name]/index.js',
    path: path.resolve(__dirname, 'dist'),
    libraryTarget: 'commonjs'
  }
};