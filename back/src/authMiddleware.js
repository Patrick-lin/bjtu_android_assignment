const headerField = 'authorization';
const authPrefix = 'TOKEN ';

import { Users } from './db';

export default () => async (req, res, next) => {
  try {
    const auth = req.header(headerField);
    if (auth && auth.startsWith(authPrefix)) {
      const token = auth.slice(authPrefix.length);
      req.user = await Users.findOne({ _id: token });
    }
  } finally {
    next();
  }
}
