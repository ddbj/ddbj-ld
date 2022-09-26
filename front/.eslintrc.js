module.exports = {
  'extends': 'next/core-web-vitals',
  'rules'  : {
    'indent'              : ['error', 2],
    'linebreak-style'     : ['error', 'unix'],
    'quotes'              : ['error', 'single'],
    'semi'                : ['error', 'always'],
    'object-curly-spacing': ['error', 'always'],
    'arrow-spacing'       : ['error', { 'before': true, 'after': true }],
    'key-spacing'         : ['error', {
      'beforeColon': false,
      'afterColon' : true,
      'align'      : 'colon'
    }],
    'eol-last'               : ['error', 'always'],
    'comma-spacing'          : ['error', { 'before': false, 'after': true }],
    'space-infix-ops'        : ['error', { 'int32Hint': false }],
    'no-multiple-empty-lines': ['error', { 'max': 1 }],
    'jsx-quotes'             : ['error', 'prefer-double']
  }
};
