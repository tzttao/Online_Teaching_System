const path = require('path')
const webpack = require('webpack')
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer')

module.exports = {
  // Production optimization
  mode: 'production',
  
  // Code splitting configuration
  optimization: {
    splitChunks: {
      chunks: 'all',
      cacheGroups: {
        // Vendor libraries
        vendor: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendors',
          chunks: 'all',
          priority: 10
        },
        // Vue and related libraries
        vue: {
          test: /[\\/]node_modules[\\/](vue|vue-router|vuex)[\\/]/,
          name: 'vue',
          chunks: 'all',
          priority: 20
        },
        // Element UI
        elementUI: {
          test: /[\\/]node_modules[\\/]element-ui[\\/]/,
          name: 'element-ui',
          chunks: 'all',
          priority: 15
        },
        // Common modules
        common: {
          minChunks: 2,
          chunks: 'all',
          name: 'common',
          priority: 5
        }
      }
    },
    // Runtime chunk
    runtimeChunk: {
      name: 'runtime'
    },
    // Minimize
    minimize: true,
    // Remove unused exports
    usedExports: true,
    // Remove dead code
    sideEffects: false
  },
  
  // Performance optimization
  performance: {
    hints: 'warning',
    maxEntrypointSize: 512000, // 500KB
    maxAssetSize: 512000 // 500KB
  },
  
  // Plugins for optimization
  plugins: [
    // Define environment variables
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify('production')
    }),
    
    // Analyze bundle size (optional, enable when needed)
    // new BundleAnalyzerPlugin({
    //   analyzerMode: 'static',
    //   openAnalyzer: false,
    //   reportFilename: 'bundle-report.html'
    // })
  ],
  
  // Module resolution optimization
  resolve: {
    // Alias for commonly used paths
    alias: {
      '@': path.resolve(__dirname, 'src'),
      'components': path.resolve(__dirname, 'src/components'),
      'views': path.resolve(__dirname, 'src/views'),
      'api': path.resolve(__dirname, 'src/api'),
      'utils': path.resolve(__dirname, 'src/utils')
    },
    // Extensions to resolve
    extensions: ['.js', '.vue', '.json'],
    // Modules resolution
    modules: [
      path.resolve(__dirname, 'src'),
      'node_modules'
    ]
  },
  
  // Module rules optimization
  module: {
    rules: [
      // Vue files
      {
        test: /\.vue$/,
        use: [{
          loader: 'vue-loader',
          options: {
            // Optimize Vue components
            optimizeSSR: false,
            hotReload: false
          }
        }]
      },
      // JavaScript files
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: [{
          loader: 'babel-loader',
          options: {
            cacheDirectory: true // Enable babel cache
          }
        }]
      },
      // CSS files with optimization
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          {
            loader: 'css-loader',
            options: {
              minimize: true
            }
          },
          'postcss-loader'
        ]
      },
      // SCSS files with optimization
      {
        test: /\.scss$/,
        use: [
          'vue-style-loader',
          {
            loader: 'css-loader',
            options: {
              minimize: true
            }
          },
          'postcss-loader',
          'sass-loader'
        ]
      },
      // Images with optimization
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        use: [{
          loader: 'url-loader',
          options: {
            limit: 8192, // 8KB
            name: 'img/[name].[hash:7].[ext]'
          }
        }]
      },
      // Fonts
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        use: [{
          loader: 'url-loader',
          options: {
            limit: 8192,
            name: 'fonts/[name].[hash:7].[ext]'
          }
        }]
      }
    ]
  },
  
  // Development server optimization
  devServer: {
    compress: true,
    hot: true,
    overlay: {
      warnings: false,
      errors: true
    }
  }
}