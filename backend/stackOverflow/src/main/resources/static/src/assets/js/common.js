export const getSearch = () => {
  var search = location.search.substring(1);
  if (search === '') return false;
  var searchObj = JSON.parse(
    '{"' +
      decodeURI(search)
        .replace(/"/g, '\\"')
        .replace(/&/g, '","')
        .replace(/=/g, '":"') +
      '"}'
  );
  return searchObj;
};
