import faker from 'faker';

import { Trainers, Classes, News } from './db';
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

const toIds = elem => elem._id

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
  cover = faker.random.arrayElement([
    'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754226097,347722291&fm=15&gp=0.jpg',
    'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2642340674,2087553279&fm=15&gp=0.jpg',
    'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=298546301,221452502&fm=15&gp=0.jpg',
    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg',
    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3474596885,3589851943&fm=15&gp=0.jpg',
    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=332834531,2740295829&fm=15&gp=0.jpg',
    'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2221104227,1880545130&fm=15&gp=0.jpg',
    'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=830621634,2797568601&fm=26&gp=0.jpg',
    'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3736244080,2841172467&fm=26&gp=0.jpg',
    'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3588909357,1860370031&fm=26&gp=0.jpg'
  ]),
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
    date -= faker.random.number({ min: 6, max: 96 }) * 60 * 60 * 1000,
    yield date
  }
}

const createNewsBatch = (nb) => {
  const dateGenerator = makeDateGenerator();
  const res = [];
  for (let i = 0; i < nb; i++) {
    res.unshift(createNews({ date: dateGenerator.next().value }));
  }
  console.log(res);
  return res;
}

const initNews = () => {
  return News.insert(createNewsBatch(120));
}

export default async () => {
  await initTrainers();
  await initClasses();
  await initNews();
}
