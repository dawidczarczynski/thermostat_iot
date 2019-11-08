const nodeExternals = require('webpack-node-externals');
const path = require('path');

module.exports = {
    entry: path.resolve(__dirname, 'src/index.ts'),
    output: {
        path: path.resolve(__dirname, 'bin'),
        filename: 'index.js',
        libraryTarget: 'this'
    },
    target: 'node',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loader: 'ts-loader',
                options: {
                    transpileOnly: true
                }
            }
        ]
    },
    optimization: {
      minimize: false
    },
    devtool: 'source-map',
    resolve: {
        extensions: [ '.ts', '.tsx', '.js' ],
        alias: {
          shared: path.resolve(__dirname, '../shared'),
          contract: path.resolve(__dirname, '../contract')
        }
    },
    externals: [nodeExternals()]
};