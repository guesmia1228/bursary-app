import InputComponent from "../InputComponent.jsx";
import ConsentInput from "../ConsentInput.jsx";


const DisplayForm = () =>{



    //call a function to get from the backend the form

    /*I will bw using demo data for testing purpose*/

    const theObjectFromTheBackend = {
        form: [
            {
                section: "Section A",
                Student_FullName: "checkbox",
                Student_Age: "text"
            },
            {
                section: "Section B",
                Student_FullName: "text",
                Gender: "text"
            },
            {
                section: "Section C",
                Student_FullName: "checkbox",
                Student_Age: "password"
            },
            {
                section: "Section D",
                Student_FullName: "text",
                Student_Age: "password",
                Student_FullNameA: "checkbox",
                Student_AgeA: "password"
            }
        ]
    };

    return (
        <div>
            {theObjectFromTheBackend.form.map((section, index) => (
                <div key={index}>
                    <h3>{section.section}</h3>
                    {/* Iterate through the fields in the section */}
                    {Object.keys(section).map((fieldName, fieldIndex) => (
                        fieldName !== 'section' && (
                            <div key={fieldIndex}>
                                {section[fieldName] === 'text' && (
                                    <InputComponent filedName={fieldName} type="text" fi/>
                                )}
                                {section[fieldName] === 'password' && (
                                    <InputComponent filedName={fieldName} type="password" />
                                )}
                                {section[fieldName] === 'checkbox' && (
                                    <ConsentInput value={fieldName} />
                                )}
                                {section[fieldName] === 'gender' && (

                                )}
                            </div>
                        )
                    ))}
                </div>
            ))}
        </div>
    );
}

export default DisplayForm;