import Navbar from '.';

const stories =  {
  title    : 'Parts/Navbar',
  component: Navbar,
  argTypes : {
    onToggleSideBar: { type: 'onToggleSideBar' },
  },
};

const Template = (args) => <Navbar {...args} />;

export const Default = Template.bind({});
Default.args = {};

export default stories;
