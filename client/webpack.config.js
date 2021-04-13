const CopyWebpackPlugin = require('copy-webpack-plugin');

// issue: https://github.com/meltedspark/angular-builders/issues/235
// explanation: https://github.com/meltedspark/angular-builders/issues/235#issuecomment-464393504
// workaround: https://github.com/meltedspark/angular-builders/issues/235#issuecomment-471323007
module.exports = (config, options) => {
  const ignoreList = ['config.default.php', 'README.md'];
  ['dev', 'prod', 'docker'].forEach(function(profile) {
    if (profile !== process.env.PROFILE)
    ignoreList.push(`config.${profile}.php`);
  });
  config.plugins.push(
    new CopyWebpackPlugin([{
      from: '../api',
      to: './api',
      ignore: ignoreList,
    }])
  );
  return config;
}
