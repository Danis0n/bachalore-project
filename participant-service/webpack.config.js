const path = require("path");
const Dotenv = require('dotenv-webpack');

const outputPaths = ["./src/main/resources/static/built/bundle.js", "./target/classes/static/built/bundle.js"];

module.exports = outputPaths.map(outputPath => {
    return env => {
        return {
            devtool: 'eval-source-map',
            entry: './src/main/frontend/src/App.tsx',
            cache: true,
            mode: 'development',
            output: {
                path: __dirname,
                filename: outputPath
            },
            module: {
                rules: [
                    {
                        test: /\.tsx?$/,
                        use: {
                            loader: 'ts-loader',
                            options: {
                                compilerOptions: {
                                    noEmit: false,
                                },
                            },
                        },
                        exclude: /node_modules/,
                    },
                    {
                        test: /\.css$/i,
                        use: ["style-loader", "css-loader", 'postcss-loader'],
                    },
                ]
            },
            resolve: {
                extensions: ['.tsx', '.ts', '.js'],
            },
            plugins: [
                new Dotenv({
                    path: './src/main/frontend/src/.env'
                })
            ],
        };
    };
});