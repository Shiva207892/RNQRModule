import PropTypes from 'prop-types';
import {requireNativeComponent, ViewPropTypes} from 'react-native';
var viewProps = {
  name: 'QRModule',
  propTypes: {
    startcamera: PropTypes.number,
    ViewPropTypes,
  },
};

module.exports = requireNativeComponent('QRModule', viewProps);
