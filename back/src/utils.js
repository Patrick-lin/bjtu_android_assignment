import faker from 'faker';

export const uniqNumbers = (min, max, _nb) => {
  const nb = Math.min(max - min, _nb);
  const hash = {};

  let foundNb = 0;
  while (foundNb < nb) {
    const n = faker.random.number({ min, max });
    if (!hash[n]) {
      hash[n] = true;
      foundNb += 1;
    }
  }
  return Object.keys(hash);
};

// eslint-disable-next-line import/prefer-default-export
export const arrayElements = (array, count = 1, map = elem => elem) => {
  const indexes = uniqNumbers(0, array.length, count);
  return indexes.map(index => map(array[index], index));
};
