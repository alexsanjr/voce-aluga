import NavBar from "../../components/NavBar/NavBar";
import Banner from "../../components/Banner/Banner";
import Footer from "../../components/Footer/Footer";

import "./style.min.css";
import HomeAluguel from "../Aluguel/Aluguel";

export default function Home() {
    return (
        <>
            <NavBar />
            <section className="container-body">
                <Banner />

                <div className="center">
                    <HomeAluguel />
                </div>
            </section>
            <Footer />
        </>
    );
}
