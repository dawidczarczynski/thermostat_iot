const path = require('path');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

const sourcePath = `${__dirname}/src`;

module.exports = {
  target: 'node',
  entry: {
    changeHeaterStatus: path.resolve(sourcePath, './changeHeaterStatus/index.ts'),
    registerTemperatureSensor: path.resolve(sourcePath, './registerTemperatureSensor/index.ts'),
    getSensorConnectionString: path.resolve(sourcePath, './getSensorConnectionString/index.ts')
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