import faker from 'faker';
import { random, capitalize, get } from 'lodash';
import { Settings, Trainers, Classes, News, Stores, Products } from './db';
import { arrayElements } from './utils';

const createTrainer = () => ({
  firstName: faker.name.firstName(),
  lastName: faker.name.lastName(),
  age: faker.random.number({ min: 20, max: 35 }),
  cover: faker.random.arrayElement([
    'https://www.randomaddressgenerator.com/media/face/male36.jpg',
    'https://www.randomaddressgenerator.com/media/face/male67.jpg',
    'https://www.randomaddressgenerator.com/media/face/female72.jpg',
    'https://www.randomaddressgenerator.com/media/face/male44.jpg',
    'https://www.randomaddressgenerator.com/media/face/female40.jpg',
    'https://www.randomaddressgenerator.com/media/face/male50.jpg',
    'https://www.randomaddressgenerator.com/media/face/male32.jpg',
    'https://www.randomaddressgenerator.com/media/face/female82.jpg',
  ]),
});

const initTrainers = () => Trainers.insert([
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
]);

const createClass = ({
  type,
  title,
  availableTrainerIds,
  rank,

  choosableTrainer = false,
  nbChoosableTrainer = 1,
  maxPlaces = 1,
  takenPlaces = 0,
  tagLine = faker.lorem.sentence(3),
  cover = 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg',
}) => ({
  type,
  title,
  tagLine,
  rank,

  availableTrainerIds,
  choosableTrainer,
  nbChoosableTrainer,

  maxPlaces,
  takenPlaces,

  cover,
});

const PRIVATE_CLASS = 'privateClass';
const GROUP_CLASS = 'groupClass';

const toIds = elem => get(elem, '_id');

const initClasses = async () => {
  const trainers = await Trainers.find();
  return Classes.insert([
    createClass({
      type: PRIVATE_CLASS,
      rank: 0,
      title: 'Private training',
      availableTrainerIds: trainers.map(toIds),
      choosableTrainer: true,
    }),
    createClass({
      type: GROUP_CLASS,
      rank: 1,
      title: 'Cardio training',
      availableTrainerIds: trainers.map(toIds),
      choosableTrainer: true,
      nbChoosableTrainer: 2,
      maxPlaces: 50,
      takenPlaces: 6,
    }),
    createClass({
      type: GROUP_CLASS,
      rank: 2,
      title: 'Yoga training',
      availableTrainerIds: arrayElements(trainers, 2, toIds),
      maxPlaces: 50,
      takenPlaces: 12,
    }),
    createClass({
      type: GROUP_CLASS,
      rank: 3,
      title: 'Dance training',
      availableTrainerIds: arrayElements(trainers, 1, toIds),
      maxPlaces: 50,
      takenPlaces: 50,
    }),
  ]);
}

const createNews = ({
  title = faker.lorem.sentence(4),
  text = faker.lorem.paragraph(50),
  date,
  tagLine = faker.lorem.sentence(10),
  cover =  faker.random.arrayElement([
    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3022739984,2769289150&fm=26&gp=0.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590021&di=6087174715e58f4634f86ac3514ab447&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170812%2Fbf545fd5b78f4cb29bd3365def9ec05a_th.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590044&di=f02564199a09083fabad8d7a456131c4&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.cn314.com%2Fd%2Ffile%2Fdaogou%2Fd2888dca48df80c9452ea43738c8623e.jpg',
    'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2340864136,1320629563&fm=26&gp=0.jpg',
  ]),
  // cover = faker.random.arrayElement([
  //   'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754226097,347722291&fm=15&gp=0.jpg',
  //   'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2642340674,2087553279&fm=15&gp=0.jpg',
  //   'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=298546301,221452502&fm=15&gp=0.jpg',
  //   'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg',
  //   'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3474596885,3589851943&fm=15&gp=0.jpg',
  //   'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=332834531,2740295829&fm=15&gp=0.jpg',
  //   'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2221104227,1880545130&fm=15&gp=0.jpg',
  //   'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=830621634,2797568601&fm=26&gp=0.jpg',
  //   'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3736244080,2841172467&fm=26&gp=0.jpg',
  //   'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3588909357,1860370031&fm=26&gp=0.jpg'
  // ]),
} = {}) => ({
  title,
  text,
  date,
  tagLine,
  cover,
});


function* makeDateGenerator() {
  let date = new Date();
  yield date;

  while (true) {
    date = new Date(date.valueOf() - faker.random.number({ min: 6, max: 96 }) * 60 * 60 * 1000),
    yield date;
  }
}

const createNewsBatch = (nb) => {
  const dateGenerator = makeDateGenerator();
  const res = [];
  for (let i = 0; i < nb; i++) {
    res.unshift(createNews({ date: dateGenerator.next().value }));
  }
  return res;
}

const initNews = () => {
  return News.insert(createNewsBatch(120));
}

export const createStore = () => ({
  name: faker.company.companyName(),
  description: faker.lorem.sentences(2),
  cover: faker.random.arrayElement([
    'https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=amazon&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=1595801861,1267469603&os=1268355239,318895372&simid=0,0&pn=0&rn=1&di=85143555750&ln=1519&fr=&fmq=1545985364493_R&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170901%2F35c1492b2470402ead7bfe9bd53c40a5.jpeg&rpstart=0&rpnum=0&adpicid=0&force=undefined',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590199&di=2db9f5a93566ba804fa71860b680f083&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170618%2Fbaaaa157e4b645fca58ebda6899af1d3_th.png',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590280&di=f3d0edf651fe086a88b9543ba68b5462&imgtype=jpg&er=1&src=http%3A%2F%2Fpic2.hualongxiang.com%2Fattachment%2Fthumb%2FDay_150922%2F2218_732439_d2e0e87d8ff0f22.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590297&di=18c749fabca2fb088ea03c46e0dbfb4b&imgtype=jpg&er=1&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20164_5_10%2Fa9g1fh1042633010352.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590363&di=4033982e45a50f4ae8cee5f02e182c2b&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2014%2F0911%2F20140911104040737.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590407&di=07e935d996643fb4b991323b7b465428&imgtype=jpg&er=1&src=http%3A%2F%2Fpic23.photophoto.cn%2F20120401%2F0007019931007170_b.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590421&di=7a9711ff3648111d13c58f1ec7b25fe1&imgtype=jpg&er=1&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbainuo%2Fwh%3D720%2C436%2Fsign%3Ddf6608c0b1fd5266a77e34139928bb1f%2F8d5494eef01f3a297358d1e59e25bc315c607c0c.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590462&di=f2f24f4eb263db811228723fb699bf49&imgtype=jpg&er=1&src=http%3A%2F%2Fimg1.gtimg.com%2Fxian%2Fpics%2F28573%2F28573602.jpg',
  ]),
  sellers: [
    { name: faker.company.companyName() },
    { name: faker.company.companyName() },
    { name: faker.company.companyName() },
  ]
});

export const createProducts = store => ({
  name: faker.commerce.productName(),
  storeId: store._id,
  cover: faker.random.arrayElement([
    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3022739984,2769289150&fm=26&gp=0.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590021&di=6087174715e58f4634f86ac3514ab447&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170812%2Fbf545fd5b78f4cb29bd3365def9ec05a_th.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546590044&di=f02564199a09083fabad8d7a456131c4&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.cn314.com%2Fd%2Ffile%2Fdaogou%2Fd2888dca48df80c9452ea43738c8623e.jpg',
    'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2340864136,1320629563&fm=26&gp=0.jpg',
  ]),
  description: faker.lorem.sentences(2),
  price: random(200, 1000 * 100),
});

export const initProducts = async () => {
  const stores = await Stores.find()
  await Promise.all(stores.map(async store => {
    await Products.insert([
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
      createProducts(store),
    ]);
  }));
}

export const initStores = async () => {
  await Stores.insert([
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
    createStore(),
  ]);
}

/**
 * version data inition
 * @param {Object} arg
 * @param {Number} arg.version
 * @param {String} arg.key
 * @param {Function} arg.initData
 */
const versioning = async ({
  version,
  key = '',
  collection,
  initData,
}) => {
  const setting = await Settings.findOne({ key });
  if (!version || !setting || setting.version < version) {
    await collection.remove({}, { multi: true });
    await Settings.update({ key }, { key, version }, { upsert: true });
    return initData();
  }
}

export default async () => {
  await versioning({
    version: 1,
    key: 'trainers',
    collection: Trainers,
    initData: initTrainers,
  });
  await versioning({
    version: 1,
    key: 'classes',
    collection: Classes,
    initData: initClasses,
  });
  await versioning({
    version: 1,
    key: 'news',
    collection: News,
    initData: initNews,
  });

  await versioning({
    version: 1.1,
    key: 'stores',
    collection: Stores,
    initData: initStores,
  });

  await versioning({
    version: 1,
    key: 'products',
    collection: Products,
    initData: initProducts,
  });
}
